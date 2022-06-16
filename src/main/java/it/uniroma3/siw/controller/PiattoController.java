package it.uniroma3.siw.controller;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.controller.validator.PiattoValidator;
import it.uniroma3.siw.model.Buffet;
import it.uniroma3.siw.model.Ingrediente;
import it.uniroma3.siw.model.Piatto;
import it.uniroma3.siw.service.BuffetService;
import it.uniroma3.siw.service.IngredienteService;
import it.uniroma3.siw.service.PiattoService;


@Controller
public class PiattoController {

    @Autowired
    PiattoService piattoService;
    @Autowired
    PiattoValidator piattoValidator;

    @Autowired
    BuffetService buffetService;

    @Autowired
    IngredienteService ingredienteService;

    @GetMapping("/show/piatto/{id}")
    public String mostraPiatto(@PathVariable("id") Long id, Model model) {
        Piatto piatto = piattoService.findById(id);
        model.addAttribute("piatto", piatto);
        return "visualizzaPiatto";
    }

    @GetMapping("/admin/createpiatto")
    public String creaPiatto(Model model) {
        model.addAttribute("piatto", new Piatto());
        model.addAttribute("ingredienti", ingredienteService.findAll());
        model.addAttribute("ingredientiSelected", new LinkedList<Ingrediente>());
        model.addAttribute("idingredienti", new LinkedList<Long>());

        return "admin/createPiatto";
    }

    @PostMapping("/admin/createpiatto")
    public String ConfermaCreaPiatto(@ModelAttribute("piatto") Piatto piatto,
                                     BindingResult piattoBindingResult,
                                     @RequestParam(value = "idingredienti", required = false) List<Long> idingredienti,
                                     @RequestParam("file") MultipartFile image,
                                     Model model) {
        //Valido i dati forniti per la creazione del piatto
        this.piattoValidator.validate(piatto, piattoBindingResult);

        //Ottengo la lista degli ingredienti forniti dall'amministratore
        List<Ingrediente> ingredienti = new ArrayList<>();
        if (idingredienti != null) {
            for (Long idIngr : idingredienti) {
                ingredienti.add(this.ingredienteService.findById(idIngr));
            }
        }

        if (!piattoBindingResult.hasErrors()) {
            //Imposto gli ingredienti e salvo il piatto.
            piatto.setIngredienti(ingredienti);
            piattoService.save(piatto);
            //Imposto l'immagine per poi risalvare il piatto
            piatto.setImmagine(Shared.SavePicture(piatto.getId(), "/images/piatto/", image));
            piattoService.save(piatto);
            return "redirect:/show/piatto/" + piatto.getId();
        } else {
            model.addAttribute("ingredienti", ingredienteService.findAll());
            model.addAttribute("ingredientiSelected", ingredienti);
            return "admin/createPiatto";
        }
    }

    @GetMapping("/admin/modificapiatto/{id}")
    public String aggiornaPiatto(@PathVariable("id") Long id, Model model) {
        Piatto piatto = this.piattoService.findById(id);
        model.addAttribute("piatto", piatto);
        model.addAttribute("ingredienti", ingredienteService.findAll());
        model.addAttribute("ingredientiSelected", piatto.getIngredienti());

        return "admin/modificaPiatto";
    }

    @PostMapping("/admin/modificapiatto/{id}")
    public String confermaAggiornaPiatto(@ModelAttribute("piatto") Piatto piatto,
                                         @PathVariable("id") Long id,
                                         BindingResult piattoBindingResult,
                                         @RequestParam(value = "idingredienti", required = false) List<Long> idingredienti,
                                         @RequestParam("file") MultipartFile image,
                                         Model model) {

        //Valido che i dati necessari per l'aggiornamento siano validi
        this.piattoValidator.validateUpdate(piatto, piattoBindingResult);

        //Ottengo la lista degli ingredienti selezionati dall'amministratore
        List<Ingrediente> ingredienti = new ArrayList<>();
        if (idingredienti != null) {
            for (Long idIngr : idingredienti) {
                ingredienti.add(this.ingredienteService.findById(idIngr));
            }
        }

        if (!piattoBindingResult.hasErrors()) {
            //Ottengo il piatto originale e lo aggiorno con i valori forniti
            Piatto original = this.piattoService.findById(id);
            original.updateValues(piatto);

            //Imposto gli ingredienti e salvo il piatto
            original.setIngredienti(ingredienti);
            piattoService.save(original);
            if (!image.isEmpty()) {
                original.setImmagine(Shared.SavePicture(original.getId(), "/images/piatto/", image));
                piattoService.save(original);
            }
            return "redirect:/show/piatto/" + id;
        } else {
            model.addAttribute("piatto", piatto);
            model.addAttribute("ingredienti", ingredienteService.findAll());
            model.addAttribute("ingredientiSelected", ingredienti);
            return "admin/modificaPiatto";
        }
    }

    @GetMapping("/admin/deletepiatto/{id}")
    public String deletePiatto(Model model, @PathVariable("id") Long id) {
        Piatto piatto = this.piattoService.findById(id);
        List<Buffet> buffets = this.buffetService.findByPiatto(piatto);

        //Rimuovo il piatto da tutti i buffet
        for (Buffet b : buffets) {
            b.getPiatti().remove(piatto);
            this.buffetService.save(b);
        }

        this.piattoService.delete(piatto);

        return "redirect:/index";
    }
}

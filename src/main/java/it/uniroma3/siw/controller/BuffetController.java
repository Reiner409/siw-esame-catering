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

import it.uniroma3.siw.controller.validator.BuffetValidator;
import it.uniroma3.siw.model.Buffet;
import it.uniroma3.siw.model.Chef;
import it.uniroma3.siw.model.Piatto;
import it.uniroma3.siw.service.BuffetService;
import it.uniroma3.siw.service.ChefService;
import it.uniroma3.siw.service.PiattoService;

@Controller
public class BuffetController {
    @Autowired
    BuffetService buffetService;

    @Autowired
    BuffetValidator buffetValidator;

    @Autowired
    PiattoService piattoService;

    @Autowired
    ChefService chefService;

    private static final String pictureFolder = "/images/buffet/";


    @GetMapping("/show/allbuffet")
    public String getBuffets(Model model) {
        model.addAttribute("buffets", this.buffetService.findAllWithChef());
        return "showAllBuffet";
    }


    @GetMapping("/show/buffet/{id}")
    public String mostraBuffet(@PathVariable("id") Long id, Model model) {
        Buffet buffet = buffetService.findById(id);
        model.addAttribute("buffet", buffet);
        return "visualizzaBuffet";
    }

    @GetMapping("/admin/modificabuffet/{id}")
    public String modificaBuffet(@PathVariable("id") Long id, Model model) {
        Buffet buffet = buffetService.findById(id);
        model.addAttribute("buffet", buffet);
        model.addAttribute("chef", buffet.getChef());
        model.addAttribute("piatti", this.piattoService.findAll());
        model.addAttribute("piattiSelected", buffet.getPiatti());
        model.addAttribute("chefs", this.chefService.findAll());
        return "admin/modificaBuffet";
    }

    @PostMapping("/admin/modificabuffet/{id}")
    public String confermaModificaBuffet(@ModelAttribute("buffet") Buffet updated,
                                         BindingResult buffetBindingResult,
                                         @PathVariable("id") Long idBuffet,
                                         @RequestParam(value = "idpiatti", required = false) List<Long> idPiatti,
                                         @RequestParam(value = "idchef", required = false) Long idChef,
                                         @RequestParam("file") MultipartFile image,
                                         Model model) {

        //Valida i dati forniti per l'update del buffet
        this.buffetValidator.validateUpdate(updated, buffetBindingResult);

        //Nel caso non è stato fornito uno chef, rifiuta la conferma
        if (idChef == 0)
            buffetBindingResult.reject("buffet.noChef");

        List<Piatto> piatti = new ArrayList<>();
        if (idPiatti != null) {
            for (Long idPiatto : idPiatti) {
                piatti.add(this.piattoService.findById(idPiatto));
            }
        }

        if (!buffetBindingResult.hasErrors()) {
            Buffet buffet = this.buffetService.findById(idBuffet);
            buffet.impostaDifferenze(updated);


            buffet.setPiatti(piatti);

            if (buffet.getChef() == null || buffet.getChef().getId() != idChef) {

                //Se è presente uno chef, gli rimuovo il buffet e lo risalvo
                if (buffet.getChef() != null) {
                    Chef oldChef = buffet.getChef();
                    oldChef.removeBuffet(buffet);
                    this.chefService.save(oldChef);
                }

                //Setto lo chef, gli aggiungo il buffet e lo salvo.
                Chef chef = this.chefService.findById(idChef);
                buffet.setChef(chef);
                chef.getBuffet().add(buffet);
                this.chefService.save(chef);
            }

            //Salvo il buffet
            buffetService.save(buffet);
            if (!image.isEmpty()) {
                //Nel caso l'immagine è stata modificata, la cambio e risalvo il buffet.
                buffet.setImmagine(Shared.SavePicture(buffet.getId(), pictureFolder, image));
                buffetService.save(buffet);
            }
            return "redirect:/show/buffet/" + idBuffet;
        } else {

            //Nel caso ci sono errori, reinserisco tutti i dati per poterli
            //Riprendere da dove sono stati lasciati
            model.addAttribute("piatti", this.piattoService.findAll());
            model.addAttribute("piattiSelected", piatti);
            model.addAttribute("chefs", this.chefService.findAll());
            Buffet buffet = this.buffetService.findById(idBuffet);
            if (!(idChef != 0 && idChef != buffet.getChef().getId()))
                idChef = buffet.getChef().getId();
            model.addAttribute("chef", this.chefService.findById(idChef));
            model.addAttribute("buffet", buffet);
            return "admin/modificabuffet";
        }
    }

    @GetMapping("/admin/createbuffet")
    public String createBuffet(Model model) {
        model.addAttribute("buffet", new Buffet());
        model.addAttribute("piatti", piattoService.findAll());
        model.addAttribute("piattiSelected", new LinkedList<Piatto>());
        model.addAttribute("chefs", this.chefService.findAll());
        model.addAttribute("chef", null);

        return "admin/createBuffet";
    }

    @PostMapping("/admin/createbuffet")
    public String creaBuffet(@ModelAttribute("buffet") Buffet buffet,
                             BindingResult buffetBindingResult,
                             @RequestParam(value = "idpiatti", required = false) List<Long> idPiatti,
                             @RequestParam(value = "idchef", required = false) Long idChef,
                             @RequestParam("file") MultipartFile image,
                             Model model) {
        this.buffetValidator.validate(buffet, buffetBindingResult);

        //Prendo i piatti selezionati
        List<Piatto> piatti = new ArrayList<>();
        if (idPiatti != null) {
            for (Long idPiatto : idPiatti) {
                piatti.add(this.piattoService.findById(idPiatto));
            }
        }

        //Nel caso non è stato selezionato uno chef, rifiuto l'operazione
        if (idChef == 0)
            buffetBindingResult.reject("buffet.noChef");

        if (!buffetBindingResult.hasErrors()) {

            //Imposto i piatti al buffet
            buffet.setPiatti(piatti);

            //Imposto lo chef al buffet e lo salvo
            Chef chef = this.chefService.findById(idChef);
            buffet.setChef(chef);
            buffetService.save(buffet);

            //Aggiungo il buffet allo chef e lo salvo
            chef.getBuffet().add(buffet);
            this.chefService.save(chef);

            //Imposto l'immagine per il buffet
            buffet.setImmagine(Shared.SavePicture(buffet.getId(), pictureFolder, image));
            buffetService.save(buffet);
            return "redirect:/show/buffet/" + buffet.getId();
        } else {
            if (idChef != 0)
                model.addAttribute("chef", this.chefService.findById(idChef));
            model.addAttribute("piatti", this.piattoService.findAll());
            model.addAttribute("piattiSelected", piatti);
            model.addAttribute("chefs", this.chefService.findAll());
            return "admin/createBuffet";
        }
    }

    @GetMapping("/admin/deletebuffet/{id}")
    public String deleteBuffet(Model model, @PathVariable("id") Long id) {

        //Trovo il buffet e da esso ricavo lo chef
        Buffet buffet = this.buffetService.findById(id);
        Chef chef = buffet.getChef();

        if (chef != null) {
            //Elimino il buffet dallo chef
            chef.removeBuffet(buffet);
            buffet.setChef(null);
            this.chefService.save(chef);
        }

        //Elimino il buffet
        this.buffetService.delete(buffet);

        return "redirect:/index";
    }

}

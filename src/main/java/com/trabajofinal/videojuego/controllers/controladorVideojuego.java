package com.trabajofinal.videojuego.controllers;

import com.trabajofinal.videojuego.entities.Videojuego;
import com.trabajofinal.videojuego.services.ServicioCategoria;
import com.trabajofinal.videojuego.services.ServicioEstudio;
import com.trabajofinal.videojuego.services.ServicioVideojuego;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.validation.Valid;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.List;

@Controller
public class controladorVideojuego {
    @Autowired
    private ServicioVideojuego svcVideojuego;
    @Autowired
    private ServicioCategoria svcCategoria;
    @Autowired
    private ServicioEstudio svcEstudio;

    @GetMapping("/inicio")
    public String inicio(Model model) {
        try {
            List<Videojuego> videojuegos = this.svcVideojuego.findAllByActivo();
            model.addAttribute("videojuegos", videojuegos);

            return "views/inicio";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/detalle/{id}")
    public String detalleVideojuego(Model model, @PathVariable("id") long id) {
        try {
            Videojuego videojuego = this.svcVideojuego.findByIdAndActivo(id);
            model.addAttribute("videojuego",videojuego);
            return "views/detalle";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @GetMapping(value = "/busqueda")
    public String busquedaVideojuego(Model model, @RequestParam(value ="query",required = false)String q){
        try {
            List<Videojuego> videojuegos = this.svcVideojuego.findByTitle(q);
            model.addAttribute("videojuegos", videojuegos);
            model.addAttribute("resultado",q);
            return "views/busqueda";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/crud")
    public String crudVideojuego(Model model){
        try {
            List<Videojuego> videojuegos = this.svcVideojuego.findAll();
            model.addAttribute("videojuegos",videojuegos);
            return "views/crud";
        }catch(Exception e){
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/formulario/videojuego/{id}")
    public String formularioVideojuego(Model model,@PathVariable("id")long id){
        try {
            model.addAttribute("categorias",this.svcCategoria.findAll());
            model.addAttribute("estudios",this.svcEstudio.findAll());
            if(id==0){
                model.addAttribute("videojuego",new Videojuego());
            }else{
                model.addAttribute("videojuego",this.svcVideojuego.findById(id));
            }
            return "views/formulario/videojuego";
        }catch(Exception e){
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @PostMapping("/formulario/videojuego/{id}")
    public String guardarVideojuego(
            @RequestParam("archivo") MultipartFile archivo,
            @Valid @ModelAttribute("videojuego") Videojuego videojuego,
            BindingResult result,
            Model model,@PathVariable("id")long id
    ) git

    @GetMapping("/eliminar/videojuego/{id}")
    public String eliminarVideojuego(Model model,@PathVariable("id")long id){
        try {
            model.addAttribute("videojuego",this.svcVideojuego.findById(id));
            return "views/formulario/eliminar";
        }catch(Exception e){
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @PostMapping("/eliminar/videojuego/{id}")
    public String desactivarVideojuego(Model model,@PathVariable("id")long id){
        try {
            this.svcVideojuego.deleteById(id);
            return "redirect:/crud";
        }catch(Exception e){
            model.addAttribute("error", e.getMessage());
            System.out.println(e);
            return "error";
        }
    }

    public boolean validarExtension(MultipartFile archivo){
        try {
            ImageIO.read(archivo.getInputStream()).toString();
            return true;
        }catch (Exception e){
            System.out.println(e);
            return false;
        }
    }
}
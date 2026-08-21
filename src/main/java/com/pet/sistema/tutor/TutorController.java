package com.pet.sistema.tutor;

import com.pet.sistema.tutor.Tutor;
import com.pet.sistema.tutor.TutorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tutores")
public class TutorController {

    private final TutorService tutorService;

    public TutorController(TutorService tutorService) {
        this.tutorService = tutorService;
    }

    // Listar todos os tutores
    @GetMapping
    public List<Tutor> listarTodos() {
        return tutorService.listarTodos();
    }

    // Buscar tutor por ID
    @GetMapping("/{id}")
    public ResponseEntity<Tutor> buscarPorId(@PathVariable Long id) {
        return tutorService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Cadastrar novo tutor
    @PostMapping
    public Tutor cadastrar(@RequestBody Tutor tutor) {
        return tutorService.salvar(tutor);
    }

    // Atualizar tutor
    @PutMapping("/{id}")
    public ResponseEntity<Tutor> atualizar(
            @PathVariable Long id,
            @RequestBody Tutor tutor) {

        Tutor tutorAtualizado = tutorService.atualizar(id, tutor);
        return ResponseEntity.ok(tutorAtualizado);
    }

    // Excluir tutor
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        tutorService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
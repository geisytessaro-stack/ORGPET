package com.pet.sistema.vacina;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vacinas")
public class VacinaController {

    private final VacinaService vacinaService;

    public VacinaController(VacinaService vacinaService) {
        this.vacinaService = vacinaService;
    }

    @GetMapping
    public List<Vacina> listarTodas() {
        return vacinaService.listarTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vacina> buscarPorId(@PathVariable Long id) {
        return vacinaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Vacina cadastrar(@RequestBody Vacina vacina) {
        return vacinaService.salvar(vacina);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vacina> atualizar(@PathVariable Long id, @RequestBody Vacina vacina) {
        return ResponseEntity.ok(vacinaService.atualizar(id, vacina));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        vacinaService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

package com.otur.otur.service;

import com.otur.otur.entity.Formulario;
import com.otur.otur.entity.User;
import com.otur.otur.repository.FormularioRepository;
import com.otur.otur.repository.UserRepository;
import com.otur.otur.vo.FormularioPostVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FormularioService {

    @Autowired
    private FormularioRepository formularioRepository;

    @Autowired
    private UserRepository userRepository;

    public Formulario buscar(Long userId) {
        return formularioRepository.findById(userId).orElse(null);
    }

    public void postar(FormularioPostVO vo) {
        User user = userRepository.findById(vo.getUserId()).orElse(null);
        if (user == null) {
            return;
        }

        Formulario formulario = buscar(vo.getUserId());
        if (formulario == null) {
            formulario = new Formulario();
        }

        formulario.setUser(user);
        formulario.setJsonResponse(vo.getJsonResponse());
        formularioRepository.save(formulario);
    }
}

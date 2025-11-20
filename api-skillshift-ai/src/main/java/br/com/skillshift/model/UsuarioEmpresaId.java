package br.com.skillshift.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UsuarioEmpresaId implements Serializable {

    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(name = "id_empresa")
    private Long idEmpresa;

    public UsuarioEmpresaId() {
    }

    public UsuarioEmpresaId(Long idUsuario, Long idEmpresa) {
        this.idUsuario = idUsuario;
        this.idEmpresa = idEmpresa;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Long getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Long idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UsuarioEmpresaId that = (UsuarioEmpresaId) o;
        return Objects.equals(idUsuario, that.idUsuario) && Objects.equals(idEmpresa, that.idEmpresa);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUsuario, idEmpresa);
    }
}

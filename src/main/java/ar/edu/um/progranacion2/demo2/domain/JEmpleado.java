package ar.edu.um.progranacion2.demo2.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A JEmpleado.
 */
@Entity
@Table(name = "jempleado")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class JEmpleado implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "apellido")
    private String apellido;

    @Column(name = "puesto")
    private String puesto;

    @OneToMany(mappedBy = "empleado")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "empleado" }, allowSetters = true)
    private Set<JVenta> ventas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public JEmpleado id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public JEmpleado nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return this.apellido;
    }

    public JEmpleado apellido(String apellido) {
        this.setApellido(apellido);
        return this;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getPuesto() {
        return this.puesto;
    }

    public JEmpleado puesto(String puesto) {
        this.setPuesto(puesto);
        return this;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public Set<JVenta> getVentas() {
        return this.ventas;
    }

    public void setVentas(Set<JVenta> jVentas) {
        if (this.ventas != null) {
            this.ventas.forEach(i -> i.setEmpleado(null));
        }
        if (jVentas != null) {
            jVentas.forEach(i -> i.setEmpleado(this));
        }
        this.ventas = jVentas;
    }

    public JEmpleado ventas(Set<JVenta> jVentas) {
        this.setVentas(jVentas);
        return this;
    }

    public JEmpleado addVenta(JVenta jVenta) {
        this.ventas.add(jVenta);
        jVenta.setEmpleado(this);
        return this;
    }

    public JEmpleado removeVenta(JVenta jVenta) {
        this.ventas.remove(jVenta);
        jVenta.setEmpleado(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JEmpleado)) {
            return false;
        }
        return id != null && id.equals(((JEmpleado) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "JEmpleado{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", apellido='" + getApellido() + "'" +
            ", puesto='" + getPuesto() + "'" +
            "}";
    }
}

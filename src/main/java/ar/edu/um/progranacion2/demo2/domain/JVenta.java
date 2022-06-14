package ar.edu.um.progranacion2.demo2.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A JVenta.
 */
@Entity
@Table(name = "jventa")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class JVenta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "fecha")
    private ZonedDateTime fecha;

    @Column(name = "precio_venta")
    private Double precioVenta;

    @ManyToOne
    @JsonIgnoreProperties(value = { "ventas" }, allowSetters = true)
    private JEmpleado empleado;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public JVenta id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getFecha() {
        return this.fecha;
    }

    public JVenta fecha(ZonedDateTime fecha) {
        this.setFecha(fecha);
        return this;
    }

    public void setFecha(ZonedDateTime fecha) {
        this.fecha = fecha;
    }

    public Double getPrecioVenta() {
        return this.precioVenta;
    }

    public JVenta precioVenta(Double precioVenta) {
        this.setPrecioVenta(precioVenta);
        return this;
    }

    public void setPrecioVenta(Double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public JEmpleado getEmpleado() {
        return this.empleado;
    }

    public void setEmpleado(JEmpleado jEmpleado) {
        this.empleado = jEmpleado;
    }

    public JVenta empleado(JEmpleado jEmpleado) {
        this.setEmpleado(jEmpleado);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JVenta)) {
            return false;
        }
        return id != null && id.equals(((JVenta) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "JVenta{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            ", precioVenta=" + getPrecioVenta() +
            "}";
    }
}

package ar.edu.um.progranacion2.demo2.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ar.edu.um.progranacion2.demo2.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class JEmpleadoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(JEmpleado.class);
        JEmpleado jEmpleado1 = new JEmpleado();
        jEmpleado1.setId(1L);
        JEmpleado jEmpleado2 = new JEmpleado();
        jEmpleado2.setId(jEmpleado1.getId());
        assertThat(jEmpleado1).isEqualTo(jEmpleado2);
        jEmpleado2.setId(2L);
        assertThat(jEmpleado1).isNotEqualTo(jEmpleado2);
        jEmpleado1.setId(null);
        assertThat(jEmpleado1).isNotEqualTo(jEmpleado2);
    }
}

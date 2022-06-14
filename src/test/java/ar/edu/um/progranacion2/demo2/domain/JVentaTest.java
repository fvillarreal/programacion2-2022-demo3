package ar.edu.um.progranacion2.demo2.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ar.edu.um.progranacion2.demo2.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class JVentaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(JVenta.class);
        JVenta jVenta1 = new JVenta();
        jVenta1.setId(1L);
        JVenta jVenta2 = new JVenta();
        jVenta2.setId(jVenta1.getId());
        assertThat(jVenta1).isEqualTo(jVenta2);
        jVenta2.setId(2L);
        assertThat(jVenta1).isNotEqualTo(jVenta2);
        jVenta1.setId(null);
        assertThat(jVenta1).isNotEqualTo(jVenta2);
    }
}

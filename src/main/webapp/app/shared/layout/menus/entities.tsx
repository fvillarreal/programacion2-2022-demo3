import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { Translate, translate } from 'react-jhipster';
import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown
    icon="th-list"
    name={translate('global.menu.entities.main')}
    id="entity-menu"
    data-cy="entity"
    style={{ maxHeight: '80vh', overflow: 'auto' }}
  >
    <>{/* to avoid warnings when empty */}</>
    <MenuItem icon="asterisk" to="/persona">
      <Translate contentKey="global.menu.entities.persona" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/j-empleado">
      <Translate contentKey="global.menu.entities.jEmpleado" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/j-venta">
      <Translate contentKey="global.menu.entities.jVenta" />
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);

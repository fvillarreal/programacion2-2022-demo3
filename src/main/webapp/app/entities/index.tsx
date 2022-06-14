import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Persona from './persona';
import JEmpleado from './j-empleado';
import JVenta from './j-venta';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}persona`} component={Persona} />
      <ErrorBoundaryRoute path={`${match.url}j-empleado`} component={JEmpleado} />
      <ErrorBoundaryRoute path={`${match.url}j-venta`} component={JVenta} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;

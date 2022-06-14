import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import JEmpleado from './j-empleado';
import JEmpleadoDetail from './j-empleado-detail';
import JEmpleadoUpdate from './j-empleado-update';
import JEmpleadoDeleteDialog from './j-empleado-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={JEmpleadoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={JEmpleadoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={JEmpleadoDetail} />
      <ErrorBoundaryRoute path={match.url} component={JEmpleado} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={JEmpleadoDeleteDialog} />
  </>
);

export default Routes;

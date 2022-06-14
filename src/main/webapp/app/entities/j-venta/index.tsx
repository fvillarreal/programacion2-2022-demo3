import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import JVenta from './j-venta';
import JVentaDetail from './j-venta-detail';
import JVentaUpdate from './j-venta-update';
import JVentaDeleteDialog from './j-venta-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={JVentaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={JVentaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={JVentaDetail} />
      <ErrorBoundaryRoute path={match.url} component={JVenta} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={JVentaDeleteDialog} />
  </>
);

export default Routes;

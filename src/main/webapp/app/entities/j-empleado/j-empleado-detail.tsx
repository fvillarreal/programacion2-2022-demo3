import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './j-empleado.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const JEmpleadoDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const jEmpleadoEntity = useAppSelector(state => state.jEmpleado.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="jEmpleadoDetailsHeading">
          <Translate contentKey="demo2App.jEmpleado.detail.title">JEmpleado</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{jEmpleadoEntity.id}</dd>
          <dt>
            <span id="nombre">
              <Translate contentKey="demo2App.jEmpleado.nombre">Nombre</Translate>
            </span>
          </dt>
          <dd>{jEmpleadoEntity.nombre}</dd>
          <dt>
            <span id="apellido">
              <Translate contentKey="demo2App.jEmpleado.apellido">Apellido</Translate>
            </span>
          </dt>
          <dd>{jEmpleadoEntity.apellido}</dd>
          <dt>
            <span id="puesto">
              <Translate contentKey="demo2App.jEmpleado.puesto">Puesto</Translate>
            </span>
          </dt>
          <dd>{jEmpleadoEntity.puesto}</dd>
        </dl>
        <Button tag={Link} to="/j-empleado" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/j-empleado/${jEmpleadoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default JEmpleadoDetail;

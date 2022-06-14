import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './j-venta.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const JVentaDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const jVentaEntity = useAppSelector(state => state.jVenta.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="jVentaDetailsHeading">
          <Translate contentKey="demo2App.jVenta.detail.title">JVenta</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{jVentaEntity.id}</dd>
          <dt>
            <span id="fecha">
              <Translate contentKey="demo2App.jVenta.fecha">Fecha</Translate>
            </span>
          </dt>
          <dd>{jVentaEntity.fecha ? <TextFormat value={jVentaEntity.fecha} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="precioVenta">
              <Translate contentKey="demo2App.jVenta.precioVenta">Precio Venta</Translate>
            </span>
          </dt>
          <dd>{jVentaEntity.precioVenta}</dd>
          <dt>
            <Translate contentKey="demo2App.jVenta.empleado">Empleado</Translate>
          </dt>
          <dd>{jVentaEntity.empleado ? jVentaEntity.empleado.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/j-venta" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/j-venta/${jVentaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default JVentaDetail;

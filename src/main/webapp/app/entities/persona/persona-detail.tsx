import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './persona.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const PersonaDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const personaEntity = useAppSelector(state => state.persona.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="personaDetailsHeading">
          <Translate contentKey="demo2App.persona.detail.title">Persona</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{personaEntity.id}</dd>
          <dt>
            <span id="nombre">
              <Translate contentKey="demo2App.persona.nombre">Nombre</Translate>
            </span>
          </dt>
          <dd>{personaEntity.nombre}</dd>
          <dt>
            <span id="apellido">
              <Translate contentKey="demo2App.persona.apellido">Apellido</Translate>
            </span>
          </dt>
          <dd>{personaEntity.apellido}</dd>
        </dl>
        <Button tag={Link} to="/persona" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/persona/${personaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PersonaDetail;

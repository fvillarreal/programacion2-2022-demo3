import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IJEmpleado } from 'app/shared/model/j-empleado.model';
import { getEntities as getJEmpleados } from 'app/entities/j-empleado/j-empleado.reducer';
import { getEntity, updateEntity, createEntity, reset } from './j-venta.reducer';
import { IJVenta } from 'app/shared/model/j-venta.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const JVentaUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const jEmpleados = useAppSelector(state => state.jEmpleado.entities);
  const jVentaEntity = useAppSelector(state => state.jVenta.entity);
  const loading = useAppSelector(state => state.jVenta.loading);
  const updating = useAppSelector(state => state.jVenta.updating);
  const updateSuccess = useAppSelector(state => state.jVenta.updateSuccess);
  const handleClose = () => {
    props.history.push('/j-venta' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getJEmpleados({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.fecha = convertDateTimeToServer(values.fecha);

    const entity = {
      ...jVentaEntity,
      ...values,
      empleado: jEmpleados.find(it => it.id.toString() === values.empleado.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          fecha: displayDefaultDateTime(),
        }
      : {
          ...jVentaEntity,
          fecha: convertDateTimeFromServer(jVentaEntity.fecha),
          empleado: jVentaEntity?.empleado?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo2App.jVenta.home.createOrEditLabel" data-cy="JVentaCreateUpdateHeading">
            <Translate contentKey="demo2App.jVenta.home.createOrEditLabel">Create or edit a JVenta</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="j-venta-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('demo2App.jVenta.fecha')}
                id="j-venta-fecha"
                name="fecha"
                data-cy="fecha"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('demo2App.jVenta.precioVenta')}
                id="j-venta-precioVenta"
                name="precioVenta"
                data-cy="precioVenta"
                type="text"
              />
              <ValidatedField
                id="j-venta-empleado"
                name="empleado"
                data-cy="empleado"
                label={translate('demo2App.jVenta.empleado')}
                type="select"
              >
                <option value="" key="0" />
                {jEmpleados
                  ? jEmpleados.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/j-venta" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default JVentaUpdate;

import { IJVenta } from 'app/shared/model/j-venta.model';

export interface IJEmpleado {
  id?: number;
  nombre?: string;
  apellido?: string | null;
  puesto?: string | null;
  ventas?: IJVenta[] | null;
}

export const defaultValue: Readonly<IJEmpleado> = {};

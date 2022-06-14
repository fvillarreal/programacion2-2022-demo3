import dayjs from 'dayjs';
import { IJEmpleado } from 'app/shared/model/j-empleado.model';

export interface IJVenta {
  id?: number;
  fecha?: string | null;
  precioVenta?: number | null;
  empleado?: IJEmpleado | null;
}

export const defaultValue: Readonly<IJVenta> = {};

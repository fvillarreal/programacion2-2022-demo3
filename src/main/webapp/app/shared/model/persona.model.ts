export interface IPersona {
  id?: number;
  nombre?: string;
  apellido?: string | null;
}

export const defaultValue: Readonly<IPersona> = {};

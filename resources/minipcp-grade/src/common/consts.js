export const API_URL = 'http://localhost:3000';
export const API_BASE = '/api/v1';
export const API_RESOURCES = {
    produtos: '/produtos',
    grade: (tipo, codigo) => `/grade/${ tipo }/${ codigo }`
};

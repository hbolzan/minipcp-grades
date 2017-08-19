import { API_URL, API_BASE } from '../common/consts';

export function getResourceUrl(resource) {
    return API_URL + API_BASE + resource;
}

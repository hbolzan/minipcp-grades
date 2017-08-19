import { FETCH_PRODUTOS, RECEIVE_PRODUTOS } from './actionTypes';

function fetchProdutos(store) {
    return { type: FETCH_PRODUTOS, store };
}

function receiveProdutos(produtos) {
    return {
        type: RECEIVE_PRODUTOS,
        produtos
    };
}


export { fetchProdutos, receiveProdutos };

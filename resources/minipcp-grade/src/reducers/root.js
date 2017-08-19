import { combineReducers } from 'redux';
import { RECEIVE_PRODUTOS, FETCH_PRODUTOS, SELECT_PRODUTO } from '../actions/actionTypes';
import { receiveProdutos } from '../actions/actions';
import { API_RESOURCES } from '../common/consts';
import { getResourceUrl } from '../common/helpers';
// import {reducer as fetchReducer} from 'react-redux-fetch';


function produtosReducer(state = { selecionado: null, produtos: [], isFetching: false }, action) {
    switch (action.type) {
        case RECEIVE_PRODUTOS:
            return {...state, produtos: action.produtos, isFetching: false };
        case SELECT_PRODUTO:
            return {...state, selecionado: action.produtos };
        case FETCH_PRODUTOS:
            if ( ! state.isFetching ) {
                fetch(getResourceUrl(API_RESOURCES.produtos)).then(
                    response => response.json().then(data => action.store.dispatch(receiveProdutos(data.data)))
                );
            }
            return state;
        default:
            return state;
    }
}


function gridReducer(state = {}, action) {
    switch (action.type) {
        case 'GET':
            return Object.assign({}, state, {
                query: fetch(`http://localhost:3000/api/v1/grade/${ action.tipo }/${ action.codigo }`)
            });
        default:
            return {};
    }
}



const miniPcpApp = combineReducers({
    produtosReducer
    // repository: fetchReducer
});

export default miniPcpApp;

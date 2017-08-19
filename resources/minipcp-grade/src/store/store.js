import { createStore, applyMiddleware } from 'redux'
import { middleware as fetchMiddleware } from 'react-redux-fetch'
import miniPcpApp from '../reducers/root';

const store = createStore(
    miniPcpApp,
    applyMiddleware(fetchMiddleware)
);

export default store;

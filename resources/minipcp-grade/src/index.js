import React from 'react';
import ReactDOM from 'react-dom';
import { Provider } from 'react-redux';
import store from './store/store';
import { fetchProdutos } from './actions/actions';
import App from './App';
import registerServiceWorker from './registerServiceWorker';

ReactDOM.render(
    <App store={ store } />,
    document.getElementById('root')
);

store.dispatch(fetchProdutos(store));

// fetch("http://localhost:3000/api/v1/grade/4/PA-01-C2").then(console.log);
// registerServiceWorker();

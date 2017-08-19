import React, { Component } from 'react';
// import logo from './logo.svg';
import './App.css';
import { Table } from './components/table.js';
import ProductsDropdown from './components/ProductsDropdown'

class App extends Component {
    render() {
        let tableData = {
            head: [{ cells: ["Primeira Coluna", "Segunda Coluna", "Terceira Coluna", "Quarta Coluna"] }],
            body: [{ cells: [<input type="text" default="1"/>, 2, 3, 4] }, { cells: [10, 20, 30, 40] }, { cells: [100, 200, 300, 400] }]
        };

        let props = this.props;

        return (
            <div className="App">
                <div className="container">
                    <div className="row">
                        <div className="col-md-12">
                            <ProductsDropdown { ...props } />
                        </div>
                    </div>
                    <div className="row">
                        <div className="col-md-12">
                            <Table data={ tableData } bordered hover striped></Table>
                        </div>
                    </div>
                    <div className="row">
                        <div className="col-md-12">

                        </div>
                    </div>
                </div>

            </div>
        );
    }
}

export default App;

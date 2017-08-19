import React from 'react';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';

const prodcutsDropdown = (props) => {
    return (
        <select>
        </select>
    )
};


const ProdcutsDropdown = connect()(prodcutsDropdown);


/*

 {produtos.map( produto => (
 <option key={ produto.tipo + ';' + produto.codigo } value={ produto.tipo + ';' + produto.codigo }>{ produto.descricao }</option>
 ))}

ProdcutsDropdown.propTypes = {
    produtos: PropTypes.arrayOf(
        PropTypes.shape({
            tipo: PropTypes.number.isRequired,
            codigo: PropTypes.string.isRequired,
            descricao: PropTypes.string.isRequired
        }).isRequired
    ).isRequired
};
*/

export default ProdcutsDropdown;

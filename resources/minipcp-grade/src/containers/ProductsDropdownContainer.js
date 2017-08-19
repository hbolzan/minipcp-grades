import { connect } from 'react-redux';
import ProdcutsDropdown from '../components/ProductsDropdown';


const mapStateToProps = (state, ownProps) => {
    return {
        produtos: state.produtos
    };
};

const ProductsDropdownContainer = connect(
    mapStateToProps,
)(ProdcutsDropdown);

export default ProductsDropdownContainer;

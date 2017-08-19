import React from 'react';

export function Table(props) {
    return (
        <table className={ getTableClasses(props) }>
            <THead rows={ props.data.head } />
            <TBody rows={ props.data.body } />
        </table>
    );
}


const tableClasses = ["striped", "hover", "condensed", "bordered"];

function getTableClasses(props) {
    return "table" +
        tableClasses.reduce((classes, className) => classes + (props[className] ? " table-" + className : ""), "");
}


function THead(props) {
    return (<thead>{ getRows(props.rows) }</thead>);
}


function TBody(props) {
    return (<tbody>{ getRows(props.rows) }</tbody>);
}


function getRows(rows) {
    if ( ! rows ) {
        return null;
    }
    return rows.map((row, index) => <Row cells={ row.cells } key={ "tbody-row-" + index } />);
}


function Row(props) {
    let cells = props.cells.map((content, index) => <Cell content={content} key={ "cell-" + index } />);
    return (
        <tr>{ cells }</tr>
    )
}


function Cell(props) {
    return <td>{ props.content }</td>
}

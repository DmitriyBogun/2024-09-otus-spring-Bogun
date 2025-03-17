import React from 'react'

const styles = {
    bookTable: {
        border: "1px solid steelblue",
        width: "300px",
        borderCollapse: "collapse",
    },

    bookTableItem: {
        padding: "5px",
        border: "1px solid steelblue"
    }
}

const Header = (props) => (
    <h1>{props.title}</h1>
);

export default class Book extends React.Component {

    constructor() {
        super();
        this.state = {book: []};
    }

    componentDidMount() {
        fetch('/api/book')
            .then(response => response.json())
            .then(book => this.setState({book}));
    }

    render() {
        return (
            <React.Fragment>
                <Header title={'Book'}/>
                <table style={styles.bookTable}>
                    <thead>
                    <tr style={styles.bookTableItem}>
                        <th style={styles.bookTableItem}>ID</th>
                        <th style={styles.bookTableItem}>Title</th>
                        <th style={styles.bookTableItem}>Author</th>
                        <th style={styles.bookTableItem}>Genre</th>
                    </tr>
                    </thead>
                    <tbody>
                    {
                        this.state.book.map((book, i) => (
                            <tr style={styles.bookTableItem} key={i}>
                                <td style={styles.bookTableItem}>{book.id}</td>
                                <td style={styles.bookTableItem}>{book.title}</td>
                                <td style={styles.bookTableItem}>{book.author}</td>
                                <td style={styles.bookTableItem}>{book.genre}</td>
                            </tr>
                        ))
                    }
                    </tbody>
                </table>
            </React.Fragment>
        )
    }
};
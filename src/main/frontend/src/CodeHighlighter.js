import React from 'react'
import {Prism as SyntaxHighlighter} from 'react-syntax-highlighter'
import {dracula} from "react-syntax-highlighter/dist/cjs/styles/prism";

const CodeHighlighter = ({codeString}) => {
    const checkTags = (code) => {
        return code.replace(/<br>/g, "\n");
    }
    return (
        <SyntaxHighlighter
            language="java"
            style={dracula}
        >
            {checkTags(codeString)}
        </SyntaxHighlighter>
    );
};

export default CodeHighlighter;
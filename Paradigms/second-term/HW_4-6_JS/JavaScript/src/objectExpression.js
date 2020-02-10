'use strict';

const vars = {
    'x': 0,
    'y': 1,
    'z': 2
};

function OperationFactory(op, opName) {
    const Operation = function (...args) {
        this.args = args;
    };
    Operation.prototype.evaluate = function (...values) {
        return op(...this.args.map((arg) => arg.evaluate(...values)));
    };
    Operation.prototype.toString = function () {
        return this.args.join(" ") + " " + opName;
    };
    Operation.prototype.prefix = function () {
        return "(" + opName + " " + this.args.map((curArg) => curArg.prefix()).join(" ") + ")";
    };
    return Operation;
}

function OperandFactory(evalFunc) {
    const Operand = function (value) {
        this.value = value;
    };
    Operand.prototype.evaluate = evalFunc;

    Operand.prototype.toString = function () {
        return this.value.toString();
    };
    Operand.prototype.prefix = Operand.prototype.toString;

    return Operand;
}

const Const = new OperandFactory(function () {
    return this.value;
});

const Variable = new OperandFactory(function (...values) {
    const ind = vars[this.value];
    return values[ind];
});

const Add = OperationFactory((x, y) => x + y, "+");

const Subtract = OperationFactory((x, y) => x - y, "-");

const Multiply = OperationFactory((x, y) => x * y, "*");

const Divide = OperationFactory((x, y) => x / y, "/");

const Negate = OperationFactory((x) => -x, "negate");

const Sum = OperationFactory((...values) => values.reduce((a, b) => a + b, 0), 'sum');

const Avg = OperationFactory((...values) => values.reduce((a, b) => a + b, 0) / values.length, 'avg');

function opsFill(name, op, argsCnt) {
    ops[name] = {Op: op, numOfArgs: argsCnt};
}

let ops = {};

opsFill('+', Add, 2);
opsFill('*', Multiply, 2);
opsFill('-', Subtract, 2);
opsFill('/', Divide, 2);
opsFill('sum', Sum, undefined);
opsFill('avg', Avg, undefined);
opsFill('negate', Negate, 1);

const ParsePrefixError = function (message, expression, index) {
    this.name = "ParsingException";
    if (arguments.length > 2) {
        this.message = "Expected " + message + ', found ' + expression[index - 1]
            + " at index " + index + '\n"' + expression + '"\n';
    } else {
        this.message = message + '\n"' + expression + '"\n';
    }
};

ParsePrefixError.prototype = Error.prototype;

const Tokenizer = function (string) {
    this.index = 0;
    this.prevToken = '';
    this.curToken = '';

    const isWhitespace = function (c) {
        return /\s/.test(c);
    };

    this.checkPos = () => this.index < string.length;

    this.symbol = () => string[this.index];

    this.isBracket = () => this.symbol() === ')' || this.symbol() === '(';

    this.next = () => {
        this.index++;
    };

    this.nextToken = function () {
        this.prevToken = this.curToken;
        while (this.index < string.length && isWhitespace(string[this.index])) {
            this.index++;
        }
        this.curToken = '';
        if (this.isBracket()) {
            this.curToken = this.symbol();
            this.next();
        } else {
            while (this.checkPos() && !(this.isBracket() || isWhitespace(this.symbol()))) {
                this.curToken += this.symbol();
                this.next();
            }
        }
    };
};

const parseOperand = function (tokenizer, parseExpression, expression) {
    if (tokenizer.curToken === '(') {
        return parseExpression();
    } else if (tokenizer.curToken in vars) {
        tokenizer.nextToken();
        return new Variable(tokenizer.prevToken)
    } else if (tokenizer.curToken !== '' && !isNaN(tokenizer.curToken)) {
        tokenizer.nextToken();
        return new Const(parseInt(tokenizer.prevToken, 10));
    } else {
        throw new ParsePrefixError('operand', expression, tokenizer.index,);
    }
};

const parsePrefix = function (expression) {
    const tokenizer = new Tokenizer(expression);

    if (expression === '') {
        throw new ParsePrefixError('Expression is not defined', '');
    }

    let parseExpression = function () {
        if (tokenizer.curToken === '(') {
            tokenizer.nextToken();
            if (!(tokenizer.curToken in ops)) {
                throw new ParsePrefixError('operation', expression, tokenizer.index);
            }
            const opParams = ops[tokenizer.curToken];
            tokenizer.nextToken();
            let curArgs = [];
            while (tokenizer.curToken !== ')') {
                curArgs.push(parseOperand(tokenizer, parseExpression, expression));
                if (tokenizer.curToken === '') {
                    throw new ParsePrefixError('Missing closing parenthesis', expression);
                }
            }
            const argsCnt = opParams.numOfArgs;
            const curArgsCnt = curArgs.length;
            if (!isNaN(argsCnt) && argsCnt !== curArgsCnt) {
                throw new ParsePrefixError('Expected ' + argsCnt + ' operand(s), found ' + curArgsCnt, expression);
            }
            tokenizer.nextToken();
            return new opParams.Op(...curArgs);
        } else {
            return parseOperand(tokenizer, parseExpression, expression);
        }
    };

    tokenizer.nextToken();
    const result = parseExpression();
    if (tokenizer.curToken !== '') {
        throw new ParsePrefixError('end of expression', expression, tokenizer.index);
    }
    return result;
};


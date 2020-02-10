const vars = {
    'x': 0,
    'y': 1,
    'z': 2,
};

const operationFactory =
    (op) => (...operands) => (...values) => op(...operands.map(curOperand => curOperand(...values)));

const cnst = (value) => () => value;

const variable = (name) => (...values) => {
    const ind = vars[name];
    return values[ind];
};

const add = operationFactory((a, b) => a + b);

const subtract = operationFactory((a, b) => a - b);

const multiply = operationFactory((a, b) => a * b);

const divide = operationFactory((a, b) => a / b);

const negate = operationFactory((a) => -a);

const avg5 = operationFactory((...operands) => operands.reduce((sum, cur) => sum + cur) / operands.length);

const med3 = operationFactory((...operands) =>
    operands.sort((a, b) => a - b)[Math.floor(operands.length / 2)]);

const pi = cnst(Math.PI);
const e = cnst(Math.E);

const constants = {
    "pi": pi,
    "e": e,
};

const VARIABLES = {};
for (let v in vars) {
    VARIABLES[v] = variable(v);
}

const operations = {
    '+': [add, 2],
    '-': [subtract, 2],
    '*': [multiply, 2],
    '/': [divide, 2],
    'med3': [med3, 3],
    'avg5': [avg5, 5],
    'negate': [negate, 1],
};

const parse = (expression) => {
    let stack = [];
    expression.split(' ').filter(token => token.length > 0).forEach(token => {
        if (token in operations) {
            const [op, numberOfArgs] = operations[token];
            stack.push(op(...stack.splice(-numberOfArgs)));
        } else if (token in constants) {
            stack.push(constants[token]);
        } else if (token in VARIABLES) {
            stack.push(VARIABLES[token]);
        } else {
            stack.push(cnst(Number(token)));
        }
    });
    return stack.pop();
};

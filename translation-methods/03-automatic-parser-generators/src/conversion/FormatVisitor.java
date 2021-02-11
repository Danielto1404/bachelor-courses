package conversion;

import antlr.CPPBaseVisitor;
import antlr.CPPParser;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.TerminalNode;

public class FormatVisitor extends CPPBaseVisitor<Void> {
    private final StringBuilder programCode = new StringBuilder();

    private final String tab = " ".repeat(2);

    private int indent = 0;

    public String getFormattedCode() {
        return programCode.toString();
    }


    private void newLine() {
        programCode.append("\n");
        programCode.append(tab.repeat(Math.max(0, indent)));
    }

    private void newLine(int offset) {
        indent += offset;
        newLine();
    }

    @Override
    public Void visitTerminal(TerminalNode node) {
        if (node.getSymbol().getType() != Token.EOF) {
            programCode.append(node.getText());
        }
        return null;
    }

    @Override
    public Void visitAssList1(CPPParser.AssList1Context ctx) {
        visit(ctx.args);
        programCode.append(", ");
        visit(ctx.assign);
        return null;
    }

    @Override
    public Void visitMultOp(CPPParser.MultOpContext ctx) {
        visit(ctx.mulExpr);
        programCode.append(" ").append(ctx.op.getText()).append(" ");
        visit(ctx.unary);
        return null;
    }

    @Override
    public Void visitAddOp(CPPParser.AddOpContext ctx) {
        visit(ctx.addExpr);
        programCode.append(" ").append(ctx.op.getText()).append(" ");
        visit(ctx.mulExpr);
        return null;
    }

    @Override
    public Void visitRelOp(CPPParser.RelOpContext ctx) {
        visit(ctx.relExpr);
        programCode.append(" ").append(ctx.op.getText()).append(" ");
        visit(ctx.addExpr);
        return null;
    }

    @Override
    public Void visitEqOp(CPPParser.EqOpContext ctx) {
        visit(ctx.eqExpr);
        programCode.append(" ").append(ctx.op.getText()).append(" ");
        visit(ctx.relExpr);
        return null;
    }

    @Override
    public Void visitAndOp(CPPParser.AndOpContext ctx) {
        visit(ctx.logicalAnd);
        programCode.append(" ").append(ctx.op.getText()).append(" ");
        visit(ctx.eqExpr);
        return null;
    }

    @Override
    public Void visitOrOp(CPPParser.OrOpContext ctx) {
        visit(ctx.logicalOr);
        programCode.append(" ").append(ctx.op.getText()).append(" ");
        visit(ctx.logicalAnd);
        return null;
    }

    @Override
    public Void visitAssignExprList(CPPParser.AssignExprListContext ctx) {
        visit(ctx.unary);
        programCode.append(" ").append(ctx.op.getText()).append(" ");
        visit(ctx.assignExpr);
        return null;
    }


    @Override
    public Void visitDeclaration(CPPParser.DeclarationContext ctx) {
        visit(ctx.t);
        programCode.append(" ");
        visit(ctx.decl);
        programCode.append(";");
        return null;
    }

    @Override
    public Void visitInitDeclEq(CPPParser.InitDeclEqContext ctx) {
        visit(ctx.e1);
        programCode.append(" = ");
        visit(ctx.e2);
        return null;
    }

    @Override
    public Void visitParamListComma(CPPParser.ParamListCommaContext ctx) {
        visit(ctx.e1);
        programCode.append(", ");
        visit(ctx.e2);
        return null;
    }

    @Override
    public Void visitArgDeclaration(CPPParser.ArgDeclarationContext ctx) {
        visit(ctx.t);
        programCode.append(" ").append(ctx.argName.getText());
        return null;
    }

    @Override
    public Void visitCodeBlock(CPPParser.CodeBlockContext ctx) {
        newLine();
        programCode.append("{");
        if (ctx.scopedCode != null) {
            newLine(1);
            visit(ctx.scopedCode);
            newLine(-1);
        }
        programCode.append("}");
//        if (!ctx.ignoreNextLine) {
//            newLine();
//        }
        return null;
    }

    @Override
    public Void visitBlockItemListN(CPPParser.BlockItemListNContext ctx) {
        visit(ctx.list);
        if (!ctx.list.ign) {
            newLine();
        }
        visit(ctx.item);
        return null;
    }

    @Override
    public Void visitIfBlock(CPPParser.IfBlockContext statement) {
        programCode.append("if (");
        visit(statement.condition);
        programCode.append(") ");
        statement.ifCode.ignoreNextLine = true;
        visit(statement.ifCode);
        if (statement.elseCode != null) {
            programCode.append(" else ");
            statement.elseCode.ignoreNextLine = true;
            visit(statement.elseCode);
        }
        return null;
    }

    @Override
    public Void visitIterationBlock(CPPParser.IterationBlockContext ctx) {
        programCode.append(ctx.name.getText());
        if (!ctx.name.getText().equals("do")) {
            programCode.append(" (");
            visit(ctx.e1 != null ? ctx.e1 : ctx.forCond); // If null then for statement
            programCode.append(") ");
            ctx.code.ignoreNextLine = true;
            visit(ctx.code);
        } else {
            visit(ctx.code);
            programCode.append(" while (");
            visit(ctx.e1);
            programCode.append(")");
        }
        return null;
    }

    @Override
    public Void visitForCondition(CPPParser.ForConditionContext ctx) {
        if (ctx.d != null || ctx.assignExpr != null) {
            visit(ctx.d != null ? ctx.d : ctx.assignExpr);
        }
        programCode.append(";").append(ctx.e1 != null ? " " : "");
        if (ctx.e1 != null) {
            visit(ctx.e1);
        }
        programCode.append(";").append(ctx.e2 != null ? " " : "");
        if (ctx.e2 != null) {
            visit(ctx.e2);
        }
        return null;
    }

    @Override
    public Void visitForDeclaration(CPPParser.ForDeclarationContext ctx) {
        visit(ctx.t);
        programCode.append(" ");
        visit(ctx.initDecl);
        return null;
    }


    @Override
    public Void visitForExpr1(CPPParser.ForExpr1Context ctx) {
        visit(ctx.forExprList);
        programCode.append(", ");
        visit(ctx.assignExpr);
        return null;
    }

    @Override
    public Void visitJumpReturn(CPPParser.JumpReturnContext ctx) {
        programCode.append("return");
        if (ctx.returnValue != null) {
            programCode.append(" ");
            visit(ctx.returnValue);
        }
        programCode.append(";");
        return null;
    }

    @Override
    public Void visitFunction(CPPParser.FunctionContext function) {
        visit(function.returnType);
        programCode.append(" ");
        visit(function.definition);
        programCode.append(" ");
        visit(function.code);
        newLine();
        newLine();
        return null;
    }

    @Override
    public Void visitStruct(CPPParser.StructContext struct) {
        programCode.append("struct ").append(struct.n.getText()).append(" ").append("{");
        if (struct.list != null) {
            newLine(1);
            visit(struct.list);
            newLine(-1);
        }
        programCode.append("}");
        newLine();
        newLine();
        return null;
    }

    @Override
    public Void visitStructDec1(CPPParser.StructDec1Context ctx) {
        visit(ctx.fields);
        newLine();
        visit(ctx.decl);
        return null;
    }

    @Override
    public Void visitExprEmpty(CPPParser.ExprEmptyContext ctx) {
        return null;
    }

}

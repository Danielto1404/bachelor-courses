#include <iostream>
#include "lexicalAnalyzer.h"
#include "parser.h"
#include <graphviz/gvc.h>
#include "fstream"

using namespace std;

char* LABEL = "label";
static int count=0;

Agnode_t* transfer_tree_to_graph(std::shared_ptr<GrammarNode> node, Agraph_t* graph){
    char* node_name = &(string("node_"+to_string(count++)))[0];
    Agnode_t *gr_node =  agnode(graph,node_name,true);
    char* node_label = &(node->interp)[0];
    agset(gr_node,LABEL,node_label);
    if(node->type==node->TERM) {
        agset(gr_node,"color","turquoise");
    }

    for (std::shared_ptr<GrammarNode> child : node->children){
        Agnode_t *gr_child = transfer_tree_to_graph(child,graph);
        agedge(graph,gr_node,gr_child, nullptr,true);
    }

    return gr_node;
}

void create_pic_from_tree(string file_name,std::shared_ptr<GrammarNode> tree){

    GVC_t* gvc = gvContext();

    char* graph_name = "boolGraph";
    Agraph_t *graph = agopen(graph_name,Agstrictdirected,nullptr);
    agattr(graph,AGNODE,"color","black");
    transfer_tree_to_graph(tree,graph);

    gvLayout(gvc,graph,"dot");

    FILE* pic_file = fopen((file_name + ".png").c_str(), "w");
    gvRender(gvc,graph,"png", pic_file);
    gvFreeLayout(gvc,graph);
    agclose(graph);

    fclose(pic_file);
}

string tests[] ={"a or b and c",
                 "a and b or c",
                 "a or b or c",
                 "a or (b or c) or (d or e) or f or j",
                 "(((((a) or ((b)) )) and e))",
                 "    (   x    and not a and \na xor e \t)      ",
                 "not dsadfggffgggfssdffsasdfggghghdfdsdsfeweerer and WEOadsfSADzZaA",
                 "not a and not not (not x and not (not y)) or a",
                 "\tnot (first and second or third)",
                 "   (a and b) or not c xor not (x xor (a or not    b))",
                 " (  not  qwe and not  weq and not eqw) xor wqe xor qew xor ewq "
};

int main() {

    parser p;
    for (int i = 0; i<=10;i++){
        auto x = p.parse(tests[i]);
        create_pic_from_tree("test"+to_string(i),x);
    }
    return 0;
}
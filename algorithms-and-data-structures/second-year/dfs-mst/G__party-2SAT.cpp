#include <iostream>
#include <vector>
#include <algorithm>
#include <map>

#define c_boost std::ios_base::sync_with_stdio(false); std::cin.tie(nullptr)

using namespace std;
using graph = vector <vector <int>>;

int maxComponent = 0;

graph g, reversed_g;
map <string, int> nameIndex;
vector <string> names;
vector <string> invited;
vector <int> components;
vector <int> order;
vector <bool> used;

int getReversedVertex(int v) {
    // Если истина то веришна четная => для противоположной вершины значение
    // будет нечетное
    // на 1 больше и наоборот
    return v % 2 == 0 ? v + 1 : v - 1;
}

void readGraph(int V, int E) {
    string name, first_name, tmp, second_name;
    for (int i = 0; i < V; ++i) {
        cin >> name;
        names.push_back(name);
        nameIndex[name] = i;
    }
    for (int i = 0; i < E; ++i) {
        cin >> first_name >> tmp >> second_name;
        int from = 2 * nameIndex[first_name.substr(1)];
        int to = 2 * nameIndex[second_name.substr(1)];
        if (first_name[0] == '-') from++;
        if (second_name[0] == '-') to++;

        // Инверснутые вершины
        int reversed_from = getReversedVertex(from);
        int reversed_to = getReversedVertex(to);

        // Заполняем обычный граф
        g[from].push_back(to);
        g[reversed_to].push_back(reversed_from);

        // Развернутый граф
        reversed_g[to].push_back(from);
        reversed_g[reversed_from].push_back(reversed_to);
    }
}

void make_order(int from) {
    used[from] = true;
    for (int to: g[from]) {
        if (!used[to])
            make_order(to);
    }
    order.push_back(from);
}

void find_components(int from) {
    used[from] = true;
    components[from] = maxComponent;
    for (int to: reversed_g[from]) {
        if (!used[to])
            find_components(to);
    }
}

int main() {
    c_boost;
    int n, m;
    cin >> n >> m;
    g = graph(2 * n);
    reversed_g = graph(2 * n);
    used.resize(2 * n);
    components.resize(2 * n);
    readGraph(n, m);

    for (int i = 0; i < 2 * n; ++i)
        if (!used[i])
            make_order(i);

    reverse(order.begin(), order.end());
    used.assign(2 * n, false);

    for (int v: order)
        if (!used[v]) {
            maxComponent++;
            find_components(v);
        }

    for (int i = 0; i < 2 * n; i += 2)
        if (components[i] == components[i + 1]) {
            cout << -1;
            return 0;
        }


    // Так как мы обходили вершины в порядке времени выхода
    // то у нас автоматически получилась топологическая
    // сортировка сконденсираванного графа, если бы это было не так
    // То нашлось бы ребро справа налево => мы бы эту компоненту посетили позже
    // То у нас tout[C] = max(tout[v], v: C) => мы бы обходили вершины не в порядке выхода!
    //
    // То есть будем класть истину = 'приглашать' (которая находится правее лжи = 'не приглашать')
    for (int i = 0; i < 2 * n; i += 2)
        if (components[i] > components[i + 1])
            invited.push_back(names[i / 2]);

    cout << invited.size() << '\n';
    for (string& name: invited)
        cout << name << '\n';
}

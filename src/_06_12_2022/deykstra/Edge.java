package _06_12_2022.deykstra;

import java.util.*;

//Класс для хранения ребер
public class Edge {
    int source;
    int dest;
    int weight;

    public Edge(int source, int dest, int weight) {
        this.source = source;
        this.dest = dest;
        this.weight = weight;
    }
}

//Класс для хранения узлов
class Node {
    //вершина
    int vertex;
    int weight;

    public Node(int vertex, int weight) {
        this.vertex = vertex;
        this.weight = weight;
    }
}

//Класс для предоставления графического объекта
class Graph {
    //Список для предоставления СПИСКА смежности
    List<List<Edge>> adjList;

    Graph(List<Edge> edges, int n) {
        adjList = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            adjList.add(new ArrayList<>());
        }

        //добавлять ребра в наш граф
        for (Edge edge : edges) {
            adjList.get(edge.source).add(edge);
        }
    }
}

class Main {
    //Непосредственно алгоритм обхода
    public static void findShortestPaths(Graph graph, int source, int nodesNumbers) {
        //создаем очередь из узлов
        PriorityQueue<Node> minHeap;
        minHeap = new PriorityQueue<>(Comparator.comparingInt(node -> node.weight));
        minHeap.add(new Node(source, 0));

        //установить начальное расстояние как бесконечность
        List<Integer> dist;
        dist = new ArrayList<>(Collections.nCopies(nodesNumbers, Integer.MAX_VALUE));

        //расстояние от источника до себя самого == 0
        dist.set(source, 0);

        //логический массив для отслеживания вершин, минимальная стоимость
        //которых уже найдена
        boolean[] done = new boolean[nodesNumbers];
        done[source] = true;

        //нужно что-что, что хранит предыдущую вершину
        int[] prev = new int[nodesNumbers];
        prev[source] = -1;

        //Мы идем до тех пор, пока minHeap не станет пустым
        while (!minHeap.isEmpty()) {
            //Вернуть лучшую вершину
            Node node = minHeap.poll();

            //получим в номер вершины
            int vertex = node.vertex;

            //для каждого соседа
            for (Edge edge : graph.adjList.get(vertex)) {
                int v = edge.dest;
                int weight = edge.weight;

                //Дальше (шаг релаксации) мы меняем НЕ эффективный путь на эффективный
                if (!done[v] && (dist.get(vertex) + weight) < dist.get(v)) {
                    dist.set(v, dist.get(vertex) + weight);
                    prev[v] = vertex;
                    minHeap.add(new Node(v, dist.get(v)));
                }
            }

            //пометить вершину (vertex) как выполненную, что бы больше к ней не возвращаться
            done[vertex] = true;
        }

        List<Integer> route = new ArrayList<>();

        for (int i = 0; i < nodesNumbers; i++) {
            if(i != source && dist.get(i) != Integer.MAX_VALUE) {
                getRoute(prev, i, route);
                System.out.printf("Path (%d -> %d): Minimum cost = %d, Route = %s\n",
                        source, i, dist.get(i), route);
                route.clear();
            }
        }
    }

    private static void getRoute(int[] prev, int i, List<Integer> route) {
        if(i >= 0) {
            getRoute(prev, prev[i], route);
            route.add(i);
        }
    }

    public static void main(String[] args) {
        //Инициализируем ребра
        List<Edge> edges = Arrays.asList(
            new Edge(0, 1, 10),
                new Edge(0, 4, 3),
                new Edge(1,2,2),
                new Edge(1,4,4),
                new Edge(2,3,9),
                new Edge(3,2,7),
                new Edge(4,1,4),
                new Edge(4,2,8),
                new Edge(4,3,2)
        );

        //общее количество узлов
        int n = 5;

        //Строим граф
        Graph graph = new Graph(edges, n);

        //запуск...
        for (int source = 0; source < n; source++) {
            findShortestPaths(graph, source, n);
        }
    }
}
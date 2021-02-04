import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class DLLNode<E> {
    protected E element;
    protected DLLNode<E> pred, succ;

    public DLLNode(E elem, DLLNode<E> pred, DLLNode<E> succ) {
        this.element = elem;
        this.pred = pred;
        this.succ = succ;
    }

    @Override
    public String toString() {
        return "<-"+element.toString()+"->";
    }
}


class DLL<E> {
    private DLLNode<E> first, last;

    public DLL() {
        // Construct an empty SLL
        this.first = null;
        this.last = null;
    }

    public void deleteList() {
        first = null;
        last = null;
    }

    public int length() {
        int ret;
        if (first != null) {
            DLLNode<E> tmp = first;
            ret = 1;
            while (tmp.succ != null) {
                tmp = tmp.succ;
                ret++;
            }
            return ret;
        } else
            return 0;

    }

    public void insertFirst(E o) {
        DLLNode<E> ins = new DLLNode<E>(o, null, first);
        if (first == null)
            last = ins;
        else
            first.pred = ins;
        first = ins;
    }

    public void insertLast(E o) {
        if (first == null)
            insertFirst(o);
        else {
            DLLNode<E> ins = new DLLNode<E>(o, last, null);
            last.succ = ins;
            last = ins;
        }
    }

    public void insertAfter(E o, DLLNode<E> after) {
        if(after==last){
            insertLast(o);
            return;
        }
        DLLNode<E> ins = new DLLNode<E>(o, after, after.succ);
        after.succ.pred = ins;
        after.succ = ins;
    }

    public void insertBefore(E o, DLLNode<E> before) {
        if(before == first){
            insertFirst(o);
            return;
        }
        DLLNode<E> ins = new DLLNode<E>(o, before.pred, before);
        before.pred.succ = ins;
        before.pred = ins;
    }

    public E deleteFirst() {
        if (first != null) {
            DLLNode<E> tmp = first;
            first = first.succ;
            if (first != null) first.pred = null;
            if (first == null)
                last = null;
            return tmp.element;
        } else
            return null;
    }

    public E deleteLast() {
        if (first != null) {
            if (first.succ == null)
                return deleteFirst();
            else {
                DLLNode<E> tmp = last;
                last = last.pred;
                last.succ = null;
                return tmp.element;
            }
        }
        // else throw Exception
        return null;
    }


    @Override
    public String toString() {
        String ret = new String();
        if (first != null) {
            DLLNode<E> tmp = first;
            ret += tmp + "<->";
            while (tmp.succ != null) {
                tmp = tmp.succ;
                ret += tmp + "<->";
            }
        } else
            ret = "Prazna lista!!!";
        return ret;
    }

    public DLLNode<E> getFirst() {
        return first;
    }

    public DLLNode<E> getLast() {

        return last;
    }

}

public class Main {


    public static void main(String[] args) throws IOException {
        DLL<Integer> lista = new DLL<Integer>();
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        String s = stdin.readLine();
        int N = Integer.parseInt(s);
        s = stdin.readLine();
        String[] ids = s.split(" ");
        for (int i = 0; i < N; i++) {
            lista.insertLast(Integer.parseInt(ids[i]));
        }

        s = stdin.readLine();
        String interval[] = s.split(" ");
        int a = Integer.parseInt(interval[0]);
        int b = Integer.parseInt(interval[1]);

        s = stdin.readLine();
        interval = s.split(" ");
        int c = Integer.parseInt(interval[0]);
        int d = Integer.parseInt(interval[1]);


        DLL<Integer> result = vojska(lista, a, b, c, d);


        DLLNode<Integer> node = result.getFirst();
        System.out.print(node.element);
        node = node.succ;
        while(node != null){
            System.out.print(" "+node.element);
            node = node.succ;
        }

    }



    public static DLL splitInterval(DLL<Integer> lista, int a, int b){

        DLL<Integer> rezz = new DLL<>();
        DLLNode<Integer> p = lista.getFirst();

        // BASE CASE A == B
        if(a == b){
            rezz.insertLast(a);
            return rezz;
        }

        // ima povekje elementi
        boolean flag = false;
        while(p != null&&p.element != b){
            if(p.element == a){
                rezz.insertLast(a);
                flag = true;
            }
            else if(flag == true){
                rezz.insertLast(p.element);
            }

            p = p.succ;
        }
        // go nema b, go dodavame
        rezz.insertLast(b);
        return rezz;


    }

    private static DLL<Integer> vojska(DLL<Integer> lista, int a, int b, int c, int d) {

        // Vasiot kod tuka

        DLL<Integer> interval1 = splitInterval(lista,a,b);
        DLL<Integer> interval2 = splitInterval(lista,c,d);


        DLLNode<Integer> p = lista.getFirst();
        int counter = 0;
        int n = lista.length();
        DLL<Integer> rezz = new DLL<Integer>();

        while(p != null&&counter < n){
            if(p.element == a){
                // kopiraj interval 2
                DLLNode<Integer> p2 = interval2.getFirst();
                while(p2 != null){
                    rezz.insertLast(p2.element);
                    p2 = p2.succ;
                }

                while(p.element != b){
                    p = p.succ;
                    counter++;
                }
            }
            else if(p.element == c ){
                // kopiraj interval 1
                DLLNode<Integer> p2 = interval1.getFirst();
                while(p2 != null){
                    rezz.insertLast(p2.element);
                    p2 = p2.succ;
                }

                while(p.element != d){
                    p = p.succ;
                    counter++;
                }
            }
            else{
                // kopiraj orginalna lista
                rezz.insertLast(p.element);
            }

            p = p.succ;
            counter++;

        }


        // 1.1 ako ne e jazelot interal 1, prodolzi so kopiranje
        // 1.2 ako e interval1, kopiraj lista 2

        return rezz;

    }
}
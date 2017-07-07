/**
 * How to Generate Permutations
 */
/**
 * Just like generating
 * combinations, this time I'd
 * like to share my favorite
 * ways of generating
 * permutations.
 */
/**
 * permutations.png
 */
/**
 * Practically speaking, we
 * rarely encounter permutations
 * so why should we spend time
 * on it?
 *
 * Consider this: Permutations are a
 * fundamental problem in
 * computing and the first known
 * algorithms date back
 * thousands of years. We have
 * many algorithms for
 * permutations and the subject
 * is both fascinating and fun!
 */
/**
 * In this post, we will take a
 * look at key algorithms for
 * permutations. By the end, you
 * will have a good grasp of how
 * to think about permutation
 * algorithms.
 *
 * There will be a natural
 * progression from simple
 * permutation algorithms to
 * more usable ones.
 *
 * The last algorithm in this
 * post is my favorite
 * – simple, elegant, and
 * efficient to use. So let's
 * get started!
 */
/** *_Brief Recap_* */
/**
 * A “permutation”, as we may
 * remember from high school, is
 * a re-ordering of elements.
 * For example these are all the
 * permutations of three
 * elements:
 *      {1,2,3}
 *      {1,3,2}
 *      {2,1,3}
 *      {2,3,1}
 *      {3,1,2}
 *      {3,2,1}
 */
/**
 *  permutations3.png
 */
/**
 * Basically we pick the first
 * element from the n items, the
 * second from the remaining
 * (n-1) items, the third from
 * the remaining (n-2) items and
 * so on. This gives us a total
 * of
 *  n × (n-1) × (n-2)... × 2 × 1
 * items. This is, of course, the
 * [href=https://en.wikipedia.org/wiki/Factorial](definition of n!).
 */
/**
 * *_Basic Algorithm 1: Remove_*
 */
/**
 * Given we know there are n!
 * permutations of elements we
 * are lead directly to a basic
 * backtracking algorithm for
 * permutations –
 *  * Remove each element from
 *  the n elements one at a
 *  time, then append it to the
 *  (n-1)! remaining
 *  permutations.
 * This is pretty much a direct
 * definition of n!=n × (n-1)!
 * and is very simple to
 * implement:
 */
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Iterator;

class GeneratingPermutations {

public void permutation1(int[] a, int n) {
    if(n == 1) {
        System.out.println(Arrays.toString(a));
        return;
    }
    for(int i = 0;i < n;i++) {
        swap(a, i, n-1);
        permutation1(a, n-1);
        swap(a, i, n-1);
    }
}
/**
 * How efficient is this
 * algorithm? Well it is simple
 * and does O(n!) as expected
 * but it does two swaps for
 * each pass. Because n! is
 * large and swaps are
 * expensive, we should try to
 * do better.
 */
/**
 * *__Side Note: *Decrease and Conquer*
 *
 * This is an example of the
 * “decrease and conquer”
 * algorithmic strategy. On each
 * pass through the loop, we
 * peel off a value, solve the
 * rest of the problem, and then
 * make a change.
 *
 * This “decrease and conquer”
 * is typical and is known as
 * decrease-by-one. It has the
 * following characteristics:
 *  1. It divides the problem
 *  into two parts: a
 *  sub-problem of size (n -1)
 *  and a single remaining
 *  element.
 *  2. It then solves the
 *  sub-problem of size (n-1) by
 *  a recursive call (or an
 *  iterative decreasing
 *  process).
 *  3. Finally, it adds the
 *  remaining individual element
 *  back into the sub-problem’s
 *  solution.
 * Even more so than in
 * divide-and-conquer, the
 * ‘divide’ step is often
 * trivial. Most of the work
 * goes into the third step,
 * incorporating the lone
 * element into the existing
 * sub-solution.__*
 */
/**
 * *_Basic Algorithm 2: Insert_*
 */
/**
 * Now let us try again. There
 * is another very simple bottom
 * up decomposition of n! that
 * is the “opposite” of our
 * first attempt:
 *  * Insert the nth element at
 *  all possible locations of
 *  the (n-1)! remaining
 *  permutations.
 *
 * Let’s look at an example:
 *
 *                 1
 *      21                 12
 *  321 231 213        312 132 123
 *
 * This is also quite simple to implement:
 */
public ArrayList<ArrayList<Integer>> permutation2(int[] a, int n) {

    ArrayList<ArrayList<Integer>> gen = new ArrayList<>();
    if(n == 1) {
        ArrayList<Integer> new_permutation = new ArrayList<>();
        new_permutation.add(a[n-1]);
        gen.add(new_permutation);
    } else {
        Iterator<ArrayList<Integer>> itr = permutation2(a, n-1).iterator();
        while(itr.hasNext()) {
            ArrayList<Integer> permutation = itr.next();
            for(int i = 0;i <= permutation.size();i++) {
                ArrayList<Integer> new_permutation = new ArrayList<>(permutation);
                new_permutation.add(i, a[n-1]);
                gen.add(new_permutation);
            }
        }
    }

    if(n == a.length) {
        Iterator itr = gen.iterator();
        while(itr.hasNext()) System.out.println(itr.next());
    }

    return gen;
}

/**
 * How efficient is this
 * minimal-change algorithm?
 * Time-wise we can’t do much
 * better but we are generating
 * and storing all the
 * permutations from (n-1),
 * (n-2), ..., down to 1. That’s
 * expensive so let’s try again.
 */
/**
 * *__Side Note: *Minimal Change*
 *
 * The algorithm above works but
 * the output can be improved.
 * Notice that many times we are
 * simply exchanging consecutive
 * numbers – but not for the
 * step between 213 and 312.
 * This means that we can’t
 * count on our algorithm taking
 * constant time per generation
 * which could be important.
 * Adding a small tweak, we can
 * fix this:
 *  * When inserting the nth
 *    element for each of the
 *    remaining (n-1)!
 *    permutations, start from the
 *    right and move left, then
 *    start from the left and move
 *    right and so on...
 *
 *                1
 *      21                12
 *  321 231 213       123 132 312
 *
 * This will result in all steps
 * being just swaps between
 * adjacent elements. Algorithms
 * which generate permutations
 * by adjacent swaps are known
 * as “minimal change”
 * algorithms.__*
 */
/**
 * *_Basic Permutation 3: Lexicographic_*
 */
/**
 * Here is another idea. What if
 * we generated permutations
 * just by taking the existing
 * permutation and modifying it
 * slightly? This seems like a
 * nice idea too but brings up a
 * couple of difficulties:
 *  1. How would we not repeat
 *    permutations without keeping
 *    track of all permutations
 *    generated so far? We could
 *    take in permutation
 *    {...xy...}, modify it to
 *    {...yx...}, and in the very next
 *    step modify it back to
 *    {...xy...}!
 *  2. And how would we
 *    know when to stop?
 *
 * Both problems can be solved
 * by defining an ordering of
 * permutations. Once we do that
 * we can just start with the
 * “smallest” permutation and
 * increment it minimally till
 * we reach the “largest”
 * permutation.
 *
 * This is easy to do with our
 * examples so far – we’ve used
 * digits and so we can think of
 * each permutation as numbers
 * with all the ordering
 * goodness that comes from
 * that. What can we do when we
 * are given other elements?
 * Well we can simply transform
 * them into numbers as
 * “indexes” into the elements
 * given (like array indices).
 *
 * So let us look at
 * permutations as numbers:
 *      1234
 *      1243
 *      1324
 *      1342
 *      1423
 *      1432
 *      2134
 *      ...
 * In the example above we see
 * that 1 stays as the first
 * number for a long time as
 * there are many reorderings of
 * the last 3 digits which
 * increase the permutation by a
 * smaller amount.
 *
 * So when _do_ we finally “use”
 * the 1? When there are only no
 * more permutations of the last
 * 3 digits. And when are there
 * no more permutations of the
 * last 3 digits? When the last
 * 3 digits are in descending
 * order.
 *
 * Hence we only change the
 * position of a “digit” when
 * everything to the right is in
 * descending order. Therefore we
 * start with all digits in
 * ascending order and permute
 * until all they reverse
 * direction.
 *
 * How exactly is this done?
 * When everything to the right
 * of a digit is in descending
 * order, we find the next
 * largest digit and put it in
 * front and then put the
 * remaining digits back in
 * ascending order. This gives
 * us the lexicographic
 * permutation algorithm that is
 * used in the [href=https://gcc.gnu.org/onlinedocs/libstdc++/libstdc++-html-USERS-4.4/a01347.html](GNU C++
 * std::next_permutation)
 */
/**
 * *_Steinhaus–Johnson–Trotter algorithm_*
 */
/**
 * This is the most well-known
 * historically of the
 * permutation algorithms. It is
 * efficient and useful as well
 * and we now know enough to
 * understand it pretty easily.
 *
 * The algorithm derives from
 * “*Basic Permutation 2: Insert*”
 * and is, in essence, the same
 * as the “minimal change”
 * version we saw earlier. It
 * adds lexicographic ordering
 * to figure out how to generate
 * permutations and change
 * direction. We can understand
 * how it work as follows:
 *
 * + Put the nth element in all
 *   positions. This, if we look
 *   at it in action, makes it
 *   look like it is “moving” from
 *   one end to the other
 *      1 2 3 < 4
 *      1 2 < 4 3
 *      1 < 4 2 3
 *      < 4 1 2 3
 *
 * + Now generate the next
 *   permutation of the remaining
 *   (n-1)! elements by using the
 *   same logic (i.e. starting to
 *   “move” the next highest
 *   element)
 *      <4 1 < 3 2
 *
 * + Now that we have the next
 *   permutation, move the nth
 *   element again – this time in
 *   the opposite direction
 *   (exactly as we wanted in the
 *   “minimal changes” section)
 *      1 4 >< 3 2
 *      1 < 3 4 > 2
 *      1 < 3 2 4 >
 *
 * + Repeat this until nothing
 * can move.
 *
 * The algorithm itself follows:
 * + Set a direction for each
 *   position to move in
 *      < 1 < 2 < 3 < 4
 * + An element can move if it
 *   is larger than the element it
 *   is moving to
 *      < 1 < 2 < 3 < 4 (all of these can move)
 *      < 4 < 3 < 2 < 1 (none of these can move)
 * + Move the largest element
 *   that can move
 * + Change the direction of all
 *   elements that are bigger than
 *   the moved element
 */
public void sjt_algorithm(int[] a, int n) {
    int moving;
    int[] dirs = new int[a.length];
    for(int i = 0;i < dirs.length;i++) dirs[i] = -1;

    int x = 1;

    System.out.println(Arrays.toString(a));
    while((moving = get_largest_moveable(a, dirs, n)) != -1) {
        // reverse direction of all larger numbers
        for(int i = 0;i < dirs.length;i++) {
            if(a[i] > a[moving]) dirs[i] = dirs[i] * -1;
        }
        // move the current largest
        move(a, dirs, moving);
        System.out.println(Arrays.toString(a));
    }
}
private int get_largest_moveable(int[] a, int[] dirs, int n) {
    int largest_moveable = -1;
    for(int i = 0;i < n;i++) {
        int moveto = i + dirs[i];
        if((moveto >= 0 && moveto < n) && (a[i] > a[moveto])) {
            if(largest_moveable == -1 || a[largest_moveable] < a[i]) {
                largest_moveable = i;
            }
        }
    }
    return largest_moveable;
}
private void move(int[] a, int[] dirs, int moving) {
    int tmp;
    int moveto = moving + dirs[moving];

    tmp = a[moving];
    a[moving] = a[moveto];
    a[moveto] = tmp;

    tmp = dirs[moving];
    dirs[moving] = dirs[moveto];
    dirs[moveto] = tmp;
}

/**
 * *_Heap’s Algorithm_*
 */
/**
 *  Finally we come to my
 *  favorite algorithm. It is
 *  small, efficient, and
 *  elegant and brilliantly
 *  simple in concept. We use
 *  the first and simplest
 *  concept we came up with
 *  “*Basic Permutation 1:
 *  Remove*” i.e. _remove each
 *  element in turn and
 *  recursively generate the
 *  remaining permutations_.
 *
 * The problem we faced in a
 * naive implementation was we
 * had to do two swaps in order
 * to pick the next element to
 * remove.
 *
 * Now consider this – what if
 * we had some clever way to
 * keep track of which elements
 * we had already removed? Then
 * we could just swap out
 * unremoved elements and never
 * need to do the second swap to
 * restore order!
 *
 * This is basically what Heap
 * found – a method for picking
 * the element to swap so that
 * it is different in each case.
 * The way it works is:
 *
 *  * If the number of elements
 *  is odd, always pick the
 *  first one
 *  * If the number of elements
 *  is even, pick them up
 *  sequentially
 *
 * Sadly I have never been able
 * to [href=http://ruslanledesma.com/2016/06/17/why-does-heap-work.html](develop a complete
 * intuition) for why this works
 * so I just memorize it.
 *
 * Because it _is_ hard to develop
 * an intuition for Heap’s
 * Algorithm there are several
 * incorrect implementations
 * floating around the net. In
 * fact, [href=https://en.wikipedia.org/wiki/Heap%27s_algorithm](the Wikipedia page) for
 * Heap’s algorithm had a bug
 * until 2015, and had to be
 * fixed again twice in 2016
 * because it was edited
 * incorrectly. This is the
 * correct version:
 */
public void heaps_algorithm(int[] a, int n) {
    if(n == 1) {
        System.out.println(Arrays.toString(a));
        return;
    }
    for(int i = 0;i < (n - 1);i++) {
        heaps_algorithm(a, n-1);
        if(n % 2 == 0) swap(a, n-1, i);
        else swap(a, n-1, 0);
    }
    heaps_algorithm(a, n-1);
}
private void swap(int[] a, int i1, int i2) {
    int tmp = a[i1];
    a[i1] = a[i2];
    a[i2] = tmp;
}

/**
 * As you can see, it is small
 * and neat. The only tricky bit
 * you need to keep in mind is
 * that the loop index goes from
 * 0 to (n-1) which means we
 * need an extra recursive call
 * outside the loop.
 */
/**
 * I hope you have enjoyed this
 * tour and now feel that
 * generating permutations is a
 * fascinating topic with lots
 * of interesting algorithms.
 * Which methods did you like?
 * Let me know in your comments
 * below.
 */

private static int[] make_array(int n) {
    int[] a = new int[n];
    for(int i = 0;i < n;i++) a[i] = i+1;
    return a;
}
public static void main(String[] args) {
    if(args.length != 1) {
        System.out.println("Usage: GeneratingPermutations <array size>");
        return;
    }

    int n = Integer.parseInt(args[0]);
    if(n == 0) return;
    if(n == 1) {
        System.out.println("1");
        return;
    }

    System.out.println("Basic Algorithm 1: Remove");
    new GeneratingPermutations().permutation1(make_array(n), n);
    System.out.println("Basic Algorithm 2: Insert");
    new GeneratingPermutations().permutation2(make_array(n), n);
    System.out.println("Steinhaus–Johnson–Trotter Algorithm");
    new GeneratingPermutations().sjt_algorithm(make_array(n), n);
    System.out.println("Heap's Algorithm");
    new GeneratingPermutations().heaps_algorithm(make_array(n), n);
}
}

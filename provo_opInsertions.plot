set title 'provo - Insertions'
set xlabel 'time'
set ylabel 'volume'
set grid
set xrange [0:1]
set yrange [0:1]
set style line 1 lc rgb '#0060ad' pt 7 ps 0.5 lt 1 lw 2
f(x) = ((3.14159265359 * log(x)) + x)
plot f(x) title '((3.14159265359 * log(x)) + x)', '/home/ronaldo/NetBeansProjects/GitSimilarity/provo_insertionsData.txt' w p ls 1 title 'points'
set title 'provo - ChangedFiles'
set xlabel 'time'
set ylabel 'volume'
set grid
set xrange [0:1]
set yrange [0:1]
set style line 1 lc rgb '#0060ad' pt 7 ps 0.5 lt 1 lw 2
f(x) = ((x + sin(x)) + (-1.0 / x))
plot f(x) title '((x + sin(x)) + (-1.0 / x))', '/home/ronaldo/NetBeansProjects/GitSimilarity/provo_changedFilesData.txt' w p ls 1 title 'points'
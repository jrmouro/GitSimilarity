set title 'tpot - Insertions'
set xlabel 'time'
set ylabel 'volume'
set grid
set xrange [0:1]
set yrange [0:1]
set style line 1 lc rgb '#0060ad' pt 7 ps 0.5 lt 1 lw 2
f(x) = ((sin(((sin(x) - 1.0) / (-1.0))) * (x / (3.14159265359 + cos((x / x))))) * ((sin(1.0) * log(3.14159265359)) - cos(log(((x * x) / 1.0)))))
plot f(x) title '((sin(((sin(x) - 1.0) / (-1.0))) * (x / (3.14159265359 + cos((x / x))))) * ((sin(1.0) * log(3.14159265359)) - cos(log(((x * x) / 1.0)))))', '/home/ronaldo/Documentos/GitSimilarity/tpot_insertionsData.txt' w p ls 1 title 'points'
set title 'Mining-the-Social-Web - Insertions'
set xlabel 'time'
set ylabel 'volume'
set grid
set xrange [0:1]
set yrange [0:1]
set style line 1 lc rgb '#0060ad' pt 7 ps 0.5 lt 1 lw 2
f(x) = (((sin(x) / exp(exp(0.0))) * exp(x)) / (3.14159265359 / cos(x)))
plot f(x) title '(((sin(x) / exp(exp(0.0))) * exp(x)) / (3.14159265359 / cos(x)))', '/home/ronaldo/Documentos/GitSimilarity/Mining-the-Social-Web_insertionsData.txt' w p ls 1 title 'points'
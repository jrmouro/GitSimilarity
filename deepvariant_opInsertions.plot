set title 'deepvariant - Insertions'
set xlabel 'time'
set ylabel 'volume'
set grid
set xrange [0:1]
set yrange [0:1]
set style line 1 lc rgb '#0060ad' pt 7 ps 0.5 lt 1 lw 2
f(x) = ((0.05300105539227573 * (sin((cos(x) / (x * x))) + log(0.05300105539227573))) * sin(log(x)))
plot f(x) title '((0.05300105539227573 * (sin((cos(x) / (x * x))) + log(0.05300105539227573))) * sin(log(x)))', '/home/ronaldo/Documentos/GitSimilarity/deepvariant_insertionsData.txt' w p ls 1 title 'points'
set title 'tpot - Deletions'
set xlabel 'time'
set ylabel 'volume'
set grid
set xrange [0:1]
set yrange [0:1]
set style line 1 lc rgb '#0060ad' pt 7 ps 0.5 lt 1 lw 2
f(x) = ((((((-1.0) - 0.8393786650758946) * (log((1.0 * 0.8393786650758946)) * cos(1.0))) * x) * (1.0 * (1.0 - cos((cos((3.14159265359 * (x - x))) - x))))) / (cos(0.8393786650758946) * cos((-1.0))))
plot f(x) title '((((((-1.0) - 0.8393786650758946) * (log((1.0 * 0.8393786650758946)) * cos(1.0))) * x) * (1.0 * (1.0 - cos((cos((3.14159265359 * (x - x))) - x))))) / (cos(0.8393786650758946) * cos((-1.0))))', '/home/ronaldo/Documentos/GitSimilarity/tpot_deletionsData.txt' w p ls 1 title 'points'
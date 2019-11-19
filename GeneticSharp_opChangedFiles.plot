set title 'GeneticSharp - ChangedFiles'
set xlabel 'time'
set ylabel 'volume'
set grid
set xrange [0:1]
set yrange [0:1]
set style line 1 lc rgb '#0060ad' pt 7 ps 0.5 lt 1 lw 2
f(x) = (0.40443895805875685 * (log(cos(((x / x) + log(x)))) * (((sin(x) / (-1.0)) / (1.0 - 0.40443895805875685)) * cos(sin((x * x))))))
plot f(x) title '(0.40443895805875685 * (log(cos(((x / x) + log(x)))) * (((sin(x) / (-1.0)) / (1.0 - 0.40443895805875685)) * cos(sin((x * x))))))', '/home/ronaldo/Documentos/GitSimilarity/GeneticSharp_changedFilesData.txt' w p ls 1 title 'points'
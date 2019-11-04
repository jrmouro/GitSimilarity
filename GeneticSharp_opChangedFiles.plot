set title 'GeneticSharp - ChangedFiles'
set xlabel 'time'
set ylabel 'volume'
set grid
set xrange [0:1]
set yrange [0:1]
set style line 1 lc rgb '#0060ad' pt 7 ps 0.5 lt 1 lw 2
f(x) = (x * (((sin((3.14159265359 / cos(x))) * x) / cos(3.14159265359)) / 3.14159265359))
plot f(x) title '(x * (((sin((3.14159265359 / cos(x))) * x) / cos(3.14159265359)) / 3.14159265359))', '/home/ronaldo/Documentos/GitSimilarity/GeneticSharp_changedFilesData.txt' w p ls 1 title 'points'
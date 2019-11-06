set title 'GitMining - Deletions'
set xlabel 'time'
set ylabel 'volume'
set grid
set xrange [0:1]
set yrange [0:1]
set style line 1 lc rgb '#0060ad' pt 7 ps 0.5 lt 1 lw 2
f(x) = sin(((log((0.03080105800854982 + 1.0)) / sin(x)) - 0.03080105800854982))
plot f(x) title 'sin(((log((0.03080105800854982 + 1.0)) / sin(x)) - 0.03080105800854982))', '/home/ronaldo/Documentos/GitSimilarity/GitMining_deletionsData.txt' w p ls 1 title 'points'
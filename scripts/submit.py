'''
@author: Guocong Song
'''
import pandas as pd
import sys
import numpy as np
import gzip


df = pd.read_csv(sys.stdin)
p = 0.55 * df.p1 + 0.15 * df.p2 + 0.15 * df.p3 + 0.15 * df.p4
df['Predicted'] = prob = 1.0 / (1.0 + np.exp(-p))

submission = 'submission.cvs.gz'
print('saving to', submission, '...')
with gzip.open(submission, 'wt') as f:
    df[['Id', 'Predicted']].to_csv(f, index=False)

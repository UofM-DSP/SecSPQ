# SecSPQ

We designed a secure framework for outsourcing genomic data and executing SPQ on it. SPQ allows health care professionals or researchers to fetch similar genomic sequences based on a query sequence. For example, when a new patient is admitted, the medical doctor may want to retrieve data from previous patients with similar genomic sequences. The history of the previous patients may help the medical doctor determine whether the patient has a predisposition to a specific disease.

We used a compressed prefix tree-based indexing algorithm to pre-filter the search result. Execution of a query is done by traversing an encrypted tree where the distance is calculated by the hamming distance.

Technology: Java

Project Owners: Safiur Mahdi, Momin Al Aziz, Noman Mohammed

License: Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International Public License

Publication: MSR. Mahdi, MM Al Aziz, D. Alhadidi, and N. Mohammed – Secure Similar Patients Query on Encrypted Genomic Data – IEEE journal of biomedical and health informatics (JBHI), 2018. [to appear]

Disclaimer: The software is provided as-is with no warranty or support. We do not take any responsibility for any damage, loss of income, or any problems you might experience from using our software. If you have questions, you are encouraged to consult the paper and the source code. If you find our software useful, please cite our paper above.

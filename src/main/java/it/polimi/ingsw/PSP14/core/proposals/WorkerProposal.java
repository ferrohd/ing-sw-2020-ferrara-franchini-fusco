package it.polimi.ingsw.PSP14.core.proposals;

import java.util.ArrayList;
import java.util.List;

public class WorkerProposal implements Proposal {
    private int index;

    public WorkerProposal(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public static List<WorkerProposal> getDefaultProposalList() {
        List<WorkerProposal> list = new ArrayList<>();
        list.add(new WorkerProposal(0));
        list.add(new WorkerProposal(1));

        return list;
    }
}

package at.favre.lib.hood.views;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import at.favre.lib.hood.page.Page;
import at.favre.lib.hood.page.ViewTemplate;

public class DebugDataAdapter extends RecyclerView.Adapter<DebugDataAdapter.DebugViewHolder> {

    private Page page;
    private HoodDebugPageView.Config config;

    public DebugDataAdapter(Page page, HoodDebugPageView.Config config) {
        this.page = page;
        this.config = config;
    }

    @Override
    public DebugViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewTemplate<?> template = page.getTemplateForViewType(viewType);

        if (null == template) {
            throw new IllegalArgumentException("could not find view template with type " + viewType);
        }

        return new DebugViewHolder(template.constructView(parent, LayoutInflater.from(parent.getContext())));
    }

    @Override
    public void onBindViewHolder(DebugViewHolder holder, int position) {
        ViewTemplate<Object> template = page.getEntries().get(position).getViewTemplate();
        template.setContent(page.getEntries().get(position).getValue(), holder.holderView);
        if (config.showZebra) {
            template.decorateViewWithZebra(holder.holderView, position % 2 == 1);
        }
    }

    @Override
    public int getItemCount() {
        return page.getEntries().size();
    }

    @Override
    public int getItemViewType(int position) {
        return page.getEntries().get(position).getViewTemplate().getViewType();
    }

    static class DebugViewHolder extends RecyclerView.ViewHolder {
        final View holderView;

        public DebugViewHolder(View itemView) {
            super(itemView);
            holderView = itemView;
        }
    }

}

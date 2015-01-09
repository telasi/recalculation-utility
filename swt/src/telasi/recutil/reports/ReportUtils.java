package telasi.recutil.reports;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.EngineConstants;
import org.eclipse.birt.report.engine.api.HTMLRenderContext;
import org.eclipse.birt.report.engine.api.HTMLRenderOption;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportEngineFactory;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;

public class ReportUtils
{

private static boolean started = false;

@SuppressWarnings("unchecked")
public static String generateHTML(String desingRelativePath, Map scriptableJavaObjects) throws IOException, BirtException
{

	// getting design input stream
	URL url = ReportUtils.class.getResource(desingRelativePath);
	InputStream in = url.openStream();

	// startup the platform
	EngineConfig config = new EngineConfig();
	// config.setEngineHome("C:\\birt-runtime-2_1_0\\ReportEngine");
	if (!started) {
		Platform.startup(config);
		started = true;
	}

	// getting an engine instance
	IReportEngineFactory engineFactory = (IReportEngineFactory) Platform.createFactoryObject(IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY);
	IReportEngine engine = engineFactory.createReportEngine(config);

	// retrive design
	IReportRunnable design = engine.openReportDesign(in);

	// create output stream for rendering results
	OutputStream out = new ByteArrayOutputStream();

	// create rendering task (it also creates report document as a substage)
	IRunAndRenderTask task = engine.createRunAndRenderTask(design);
	HTMLRenderContext renderContext = new HTMLRenderContext();
	//renderContext.setBaseImageURL("images");
	//renderContext.setImageDirectory("images");
	HashMap contextMap = new HashMap();
	contextMap.put(EngineConstants.APPCONTEXT_HTML_RENDER_CONTEXT, renderContext);
	task.setAppContext(contextMap);
	HTMLRenderOption options = new HTMLRenderOption();
	options.setOutputStream(out);
	options.setOutputFormat("html");
	task.setRenderOption(options);

	// add scriptable objects
	if (scriptableJavaObjects != null && !scriptableJavaObjects.isEmpty()) {
		Set keys = scriptableJavaObjects.keySet();
		Iterator iter = keys.iterator();
		while (iter.hasNext()) {
			String key = (String) iter.next();
			Object scriptableJavaObject = scriptableJavaObjects.get(key);
			task.addScriptableJavaObject(key, scriptableJavaObject);
		}
	}

	// run rendering task
	task.run();
	task.close();

	// shutdown the platform
	//Platform.shutdown();

	// return results
	return out.toString();
}

}
